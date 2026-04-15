<template>
  <div class="map-picker">
    <div class="map-container" ref="mapContainer"></div>
    <div class="map-info">
      <el-input
        v-model="searchKeyword"
        placeholder="搜索地址"
        clearable
        @keyup.enter="handleSearch"
        style="margin-bottom: 8px"
      >
        <template #append>
          <el-button :icon="Search" @click="handleSearch">搜索</el-button>
        </template>
      </el-input>
      <div class="coordinate-info">
        <div class="coord-item">
          <span class="label">经度：</span>
          <span class="value">{{ currentLng || '-' }}</span>
        </div>
        <div class="coord-item">
          <span class="label">纬度：</span>
          <span class="value">{{ currentLat || '-' }}</span>
        </div>
      </div>
      <div class="tip-text">
        提示：点击地图选择位置，或拖动标记点调整位置
      </div>
    </div>
  </div>
</template>

<script setup name="MapPicker">
import { ref, onMounted, onUnmounted, watch } from 'vue'
import { Search } from '@element-plus/icons-vue'

const props = defineProps({
  longitude: {
    type: [Number, String],
    default: null
  },
  latitude: {
    type: [Number, String],
    default: null
  },
  address: {
    type: String,
    default: ''
  }
})

const emit = defineEmits(['update:longitude', 'update:latitude'])

const mapContainer = ref(null)
const searchKeyword = ref('')
const currentLng = ref(props.longitude)
const currentLat = ref(props.latitude)

let map = null
let marker = null
let geocoder = null
let placeSearch = null
let AMapLib = null

/**
 * 动态加载高德地图JS API
 */
function loadAMapScript() {
  return new Promise((resolve, reject) => {
    if (window.AMap) {
      resolve(window.AMap)
      return
    }

    window._AMapSecurityConfig = {
      securityJsCode: '' // 安全密钥，如需要可配置
    }

    const script = document.createElement('script')
    script.src = 'https://webapi.amap.com/maps?v=2.0&key=你的高德地图Key'
    script.onload = () => resolve(window.AMap)
    script.onerror = () => reject(new Error('高德地图加载失败'))
    document.head.appendChild(script)
  })
}

/**
 * 初始化地图实例
 */
async function initMap() {
  try {
    AMapLib = await loadAMapScript()

    // 默认中心点（北京）
    const center = (props.longitude && props.latitude)
      ? [parseFloat(props.longitude), parseFloat(props.latitude)]
      : [116.397428, 39.90923]

    // 创建地图实例
    map = new AMapLib.Map(mapContainer.value, {
      zoom: 15,
      center: center,
      resizeEnable: true
    })

    // 创建标记点
    marker = new AMapLib.Marker({
      position: center,
      draggable: true,
      title: '办公点位置'
    })
    marker.setMap(map)

    // 监听标记点拖拽事件
    marker.on('dragend', onMarkerDragEnd)

    // 监听地图点击事件
    map.on('click', onMapClick)

    // 初始化地理编码服务（用于地址搜索）
    AMapLib.plugin(['AMap.Geocoder', 'AMap.PlaceSearch'], function() {
      geocoder = new AMapLib.Geocoder({
        city: '全国'
      })

      placeSearch = new AMapLib.PlaceSearch({
        city: '全国',
        pageSize: 5
      })

      // 如果有初始地址，尝试进行地理编码
      if (props.address && !props.longitude) {
        geocodeAddress(props.address)
      }
    })

    // 添加工具条
    AMapLib.plugin(['AMap.ToolBar'], function() {
      const toolBar = new AMapLib.ToolBar({
        position: 'RT'
      })
      map.addControl(toolBar)
    })

    // 添加缩放控件
    AMapLib.plugin(['AMap.Scale'], function() {
      const scale = new AMapLib.Scale()
      map.addControl(scale)
    })
  } catch (error) {
    console.error('地图初始化失败:', error)
  }
}

/**
 * 地图点击事件处理
 */
function onMapClick(e) {
  const lnglat = e.lnglat
  updatePosition(lnglat.getLng(), lnglat.getLat())
}

/**
 * 标记点拖拽结束事件处理
 */
function onMarkerDragEnd(e) {
  const lnglat = e.target.getPosition()
  updatePosition(lnglat.getLng(), lnglat.getLat())
}

/**
 * 更新位置信息并触发事件
 */
function updatePosition(lng, lat) {
  currentLng.value = lng
  currentLat.value = lat

  if (marker) {
    marker.setPosition([lng, lat])
  }

  emit('update:longitude', lng)
  emit('update:latitude', lat)
}

/**
 * 地址搜索处理
 */
function handleSearch() {
  if (!searchKeyword.value || !placeSearch) {
    return
  }

  placeSearch.search(searchKeyword.value, function(status, result) {
    if (status === 'complete' && result.poiList && result.poiList.pois.length > 0) {
      const poi = result.poiList.pois[0]
      const location = poi.location

      if (location) {
        updatePosition(location.getLng(), location.getLat())

        if (map) {
          map.setZoomAndCenter(16, [location.getLng(), location.getLat()])
        }
      }
    }
  })
}

/**
 * 地址地理编码
 */
function geocodeAddress(address) {
  if (!geocoder || !address) {
    return
  }

  geocoder.getLocation(address, function(status, result) {
    if (status === 'complete' && result.geocodes.length > 0) {
      const location = result.geocodes[0].location
      updatePosition(location.getLng(), location.getLat())

      if (map) {
        map.setZoomAndCenter(16, [location.getLng(), location.getLat()])
      }
    }
  })
}

/**
 * 监听外部传入的坐标变化
 */
watch(() => props.longitude, (newVal) => {
  if (newVal && map && marker) {
    const lng = parseFloat(newVal)
    const lat = parseFloat(props.latitude)
    if (!isNaN(lng) && !isNaN(lat)) {
      marker.setPosition([lng, lat])
      map.setCenter([lng, lat])
      currentLng.value = lng
      currentLat.value = lat
    }
  }
})

watch(() => props.latitude, (newVal) => {
  if (newVal && map && marker) {
    const lng = parseFloat(props.longitude)
    const lat = parseFloat(newVal)
    if (!isNaN(lng) && !isNaN(lat)) {
      marker.setPosition([lng, lat])
      map.setCenter([lng, lat])
      currentLng.value = lng
      currentLat.value = lat
    }
  }
})

onMounted(() => {
  initMap()
})

onUnmounted(() => {
  if (map) {
    map.destroy()
    map = null
  }
})
</script>

<style scoped>
.map-picker {
  width: 100%;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  overflow: hidden;
}

.map-container {
  width: 100%;
  height: 350px;
  background-color: #f5f5f5;
}

.map-info {
  padding: 12px;
  background-color: #fafafa;
  border-top: 1px solid #dcdfe6;
}

.coordinate-info {
  display: flex;
  gap: 20px;
  margin-bottom: 8px;
}

.coord-item {
  display: flex;
  align-items: center;
}

.coord-item .label {
  font-weight: 500;
  color: #606266;
  margin-right: 4px;
}

.coord-item .value {
  color: #409eff;
  font-family: monospace;
}

.tip-text {
  color: #909399;
  font-size: 12px;
  line-height: 1.6;
}
</style>
