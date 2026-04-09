import { motion } from "motion/react";
import { Menu, X, ChevronRight, Globe, Award, Users, ArrowRight } from "lucide-react";
import { useState } from "react";

export default function App() {
  const [menuOpen, setMenuOpen] = useState(false);

  return (
    <div className="min-h-screen bg-white">
      {/* Navigation */}
      <nav className="fixed top-0 left-0 right-0 z-50 bg-white/95 backdrop-blur-sm border-b border-gray-200">
        <div className="max-w-7xl mx-auto px-6 lg:px-8">
          <div className="flex items-center justify-between h-20">
            <motion.div
              initial={{ opacity: 0, x: -20 }}
              animate={{ opacity: 1, x: 0 }}
              className="flex items-baseline gap-3"
            >
              <span className="text-2xl font-bold tracking-tight">盛骏</span>
              <span className="text-xs text-gray-400 tracking-widest">SHENGJUN</span>
            </motion.div>

            {/* Desktop Menu */}
            <div className="hidden md:flex items-center gap-8">
              <a href="#products" className="text-gray-600 hover:text-black transition-colors">产品中心</a>
              <a href="#manufacturing" className="text-gray-600 hover:text-black transition-colors">制造能力</a>
              <a href="#global" className="text-gray-600 hover:text-black transition-colors">全球布局</a>
              <a href="#about" className="text-gray-600 hover:text-black transition-colors">关于我们</a>
              <button className="px-6 py-2.5 bg-black text-white hover:bg-gray-800 transition-colors">
                合作咨询
              </button>
            </div>

            {/* Mobile Menu Button */}
            <button
              onClick={() => setMenuOpen(!menuOpen)}
              className="md:hidden p-2"
            >
              {menuOpen ? <X size={24} /> : <Menu size={24} />}
            </button>
          </div>
        </div>

        {/* Mobile Menu */}
        {menuOpen && (
          <motion.div
            initial={{ opacity: 0, y: -20 }}
            animate={{ opacity: 1, y: 0 }}
            className="md:hidden bg-white border-t border-gray-200"
          >
            <div className="px-6 py-4 space-y-4">
              <a href="#products" className="block text-gray-600">产品中心</a>
              <a href="#manufacturing" className="block text-gray-600">制造能力</a>
              <a href="#global" className="block text-gray-600">全球布局</a>
              <a href="#about" className="block text-gray-600">关于我们</a>
              <button className="w-full px-6 py-2.5 bg-black text-white">合作咨询</button>
            </div>
          </motion.div>
        )}
      </nav>

      {/* Hero Section */}
      <section className="relative h-screen flex items-center overflow-hidden">
        <div className="absolute inset-0 z-0">
          <img
            src="https://images.unsplash.com/photo-1567789884554-0b844b597180?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&q=80&w=2000"
            alt="Manufacturing"
            className="w-full h-full object-cover"
          />
          <div className="absolute inset-0 bg-gradient-to-r from-black/70 via-black/50 to-transparent" />
        </div>

        <div className="relative z-10 max-w-7xl mx-auto px-6 lg:px-8 w-full">
          <motion.div
            initial={{ opacity: 0, y: 40 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.8, delay: 0.2 }}
            className="max-w-2xl"
          >
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ duration: 0.8, delay: 0.3 }}
              className="text-sm tracking-widest text-gray-300 mb-4"
            >
              MADE IN CHINA · TRUSTED GLOBALLY
            </motion.div>
            <motion.h1
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ duration: 0.8, delay: 0.4 }}
              className="text-6xl md:text-7xl lg:text-8xl font-bold text-white mb-6 leading-tight"
            >
              中国制造
              <br />
              <span className="text-gray-300">全球品质</span>
            </motion.h1>
            <motion.p
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ duration: 0.8, delay: 0.6 }}
              className="text-xl text-gray-200 mb-8 max-w-xl"
            >
              顶级汽车零部件供应商，为全球50+国家提供高精度解决方案
            </motion.p>
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ duration: 0.8, delay: 0.8 }}
              className="flex flex-col sm:flex-row gap-4"
            >
              <button className="px-8 py-4 bg-white text-black hover:bg-gray-100 transition-colors flex items-center justify-center gap-2 group">
                获取产品目录
                <ArrowRight size={20} className="group-hover:translate-x-1 transition-transform" />
              </button>
              <button className="px-8 py-4 border-2 border-white text-white hover:bg-white hover:text-black transition-all">
                成为合作伙伴
              </button>
            </motion.div>
          </motion.div>
        </div>

        {/* Scroll Indicator */}
        <motion.div
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          transition={{ delay: 1.5 }}
          className="absolute bottom-8 left-1/2 -translate-x-1/2 z-10"
        >
          <motion.div
            animate={{ y: [0, 10, 0] }}
            transition={{ duration: 1.5, repeat: Infinity }}
            className="w-6 h-10 border-2 border-white/50 rounded-full flex items-start justify-center p-2"
          >
            <motion.div className="w-1.5 h-1.5 bg-white rounded-full" />
          </motion.div>
        </motion.div>
      </section>

      {/* Key Stats */}
      <section className="py-20 bg-black text-white">
        <div className="max-w-7xl mx-auto px-6 lg:px-8">
          <div className="grid grid-cols-2 md:grid-cols-4 gap-8 text-center">
            {[
              { value: "99.8%", label: "产品合格率", sublabel: "超越行业标准" },
              { value: "50+", label: "服务国家", sublabel: "全球化布局" },
              { value: "3000+", label: "全球客户", sublabel: "长期合作伙伴" },
              { value: "ISO/IATF", label: "双认证", sublabel: "国际质量体系" }
            ].map((stat, idx) => (
              <motion.div
                key={idx}
                initial={{ opacity: 0, y: 20 }}
                whileInView={{ opacity: 1, y: 0 }}
                viewport={{ once: true }}
                transition={{ duration: 0.6, delay: idx * 0.1 }}
              >
                <div className="text-4xl md:text-5xl font-bold mb-2">{stat.value}</div>
                <div className="text-gray-400 mb-1">{stat.label}</div>
                <div className="text-xs text-gray-600">{stat.sublabel}</div>
              </motion.div>
            ))}
          </div>
        </div>
      </section>

      {/* Trust Bar */}
      <section className="py-16 bg-gray-50 border-b border-gray-200">
        <div className="max-w-7xl mx-auto px-6 lg:px-8">
          <motion.div
            initial={{ opacity: 0 }}
            whileInView={{ opacity: 1 }}
            viewport={{ once: true }}
            className="text-center"
          >
            <p className="text-xs tracking-widest text-gray-500 mb-2">全球顶级汽车品牌的共同选择</p>
            <p className="text-sm tracking-widest text-gray-400 mb-10">TRUSTED BY LEADING AUTOMOTIVE BRANDS WORLDWIDE</p>
            <div className="grid grid-cols-2 md:grid-cols-5 gap-8 items-center justify-items-center opacity-30">
              <div className="text-2xl font-bold tracking-wider">BMW</div>
              <div className="text-2xl font-bold tracking-wider">AUDI</div>
              <div className="text-2xl font-bold tracking-wider">MERCEDES</div>
              <div className="text-2xl font-bold tracking-wider">TOYOTA</div>
              <div className="text-2xl font-bold tracking-wider">VOLKSWAGEN</div>
            </div>
          </motion.div>
        </div>
      </section>

      {/* Products Section */}
      <section id="products" className="py-32 bg-white">
        <div className="max-w-7xl mx-auto px-6 lg:px-8">
          <motion.div
            initial={{ opacity: 0, y: 40 }}
            whileInView={{ opacity: 1, y: 0 }}
            viewport={{ once: true }}
            transition={{ duration: 0.6 }}
            className="mb-16 max-w-4xl"
          >
            <div className="inline-block px-4 py-1.5 bg-gray-100 text-sm tracking-widest text-gray-600 mb-6">
              PRODUCT EXCELLENCE
            </div>
            <h2 className="text-5xl md:text-6xl font-bold mb-6">核心产品系列</h2>
            <p className="text-xl text-gray-600">
              100%符合国际OEM标准，5000+SKU覆盖全球主流车型，为世界顶级汽车品牌提供同等品质
            </p>
          </motion.div>

          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
            {[
              {
                tag: "OEM QUALITY",
                title: "发动机系统",
                desc: "符合欧VI排放标准，OEM级精密工艺",
                specs: "±0.005mm公差 | 100%德国设备加工",
                image: "https://images.unsplash.com/photo-1758563920450-e0aaf7dec734?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&q=80&w=800"
              },
              {
                tag: "TESTED & CERTIFIED",
                title: "底盘系统",
                desc: "通过50万次疲劳测试，超越OEM标准30%",
                specs: "IATF认证 | 全球主流车型适配",
                image: "https://images.unsplash.com/photo-1769218401073-71a5b1020c9b?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&q=80&w=800"
              },
              {
                tag: "PRECISION ENGINEERED",
                title: "传动系统",
                desc: "采用日本进口材料，99.9%精度保障",
                specs: "静音技术 | 延长30%使用寿命",
                image: "https://images.unsplash.com/photo-1760317890359-4e6bb111e501?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&q=80&w=800"
              }
            ].map((product, idx) => (
              <motion.div
                key={idx}
                initial={{ opacity: 0, y: 40 }}
                whileInView={{ opacity: 1, y: 0 }}
                viewport={{ once: true }}
                transition={{ duration: 0.6, delay: idx * 0.1 }}
                whileHover={{ y: -8 }}
                className="group cursor-pointer"
              >
                <div className="relative overflow-hidden mb-6 aspect-[4/3]">
                  <motion.img
                    src={product.image}
                    alt={product.title}
                    className="w-full h-full object-cover"
                    whileHover={{ scale: 1.05 }}
                    transition={{ duration: 0.4 }}
                  />
                  <div className="absolute inset-0 bg-black/0 group-hover:bg-black/20 transition-colors" />
                  <div className="absolute top-4 left-4 px-3 py-1 bg-white text-black text-xs tracking-wider">
                    {product.tag}
                  </div>
                </div>
                <h3 className="text-2xl font-semibold mb-2 group-hover:text-gray-600 transition-colors">
                  {product.title}
                </h3>
                <p className="text-gray-600 mb-3">{product.desc}</p>
                <p className="text-sm text-gray-500 mb-4 border-l-2 border-black pl-3">{product.specs}</p>
                <div className="flex items-center gap-2 text-black group-hover:gap-4 transition-all">
                  了解更多
                  <ChevronRight size={20} />
                </div>
              </motion.div>
            ))}
          </div>
        </div>
      </section>

      {/* Manufacturing Excellence */}
      <section id="manufacturing" className="py-32 bg-gray-50">
        <div className="max-w-7xl mx-auto px-6 lg:px-8">
          <div className="grid grid-cols-1 lg:grid-cols-2 gap-16 items-center">
            <motion.div
              initial={{ opacity: 0, x: -40 }}
              whileInView={{ opacity: 1, x: 0 }}
              viewport={{ once: true }}
              transition={{ duration: 0.8 }}
            >
              <div className="inline-block px-4 py-2 bg-black text-white text-sm tracking-widest mb-6">
                WORLD-CLASS MANUFACTURING
              </div>
              <h2 className="text-5xl md:text-6xl font-bold mb-6">中国智造 · 全球标准</h2>
              <p className="text-xl text-gray-600 mb-12">
                德国工艺标准，中国制造优势，打造极致性价比的高端零部件
              </p>

              <div className="space-y-8">
                {[
                  { icon: <Award size={24} />, title: "国际双认证", desc: "ISO/TS 16949 + IATF 16949 双体系认证" },
                  { icon: <Globe size={24} />, title: "全球战略布局", desc: "服务超过50个国家，3000+合作伙伴" },
                  { icon: <Users size={24} />, title: "顶尖研发团队", desc: "800+工程师，年研发投入超营收8%" }
                ].map((item, idx) => (
                  <motion.div
                    key={idx}
                    initial={{ opacity: 0, x: -20 }}
                    whileInView={{ opacity: 1, x: 0 }}
                    viewport={{ once: true }}
                    transition={{ duration: 0.6, delay: idx * 0.1 }}
                    className="flex gap-4"
                  >
                    <div className="flex-shrink-0 w-14 h-14 bg-black text-white flex items-center justify-center">
                      {item.icon}
                    </div>
                    <div>
                      <h3 className="text-xl font-semibold mb-1">{item.title}</h3>
                      <p className="text-gray-600">{item.desc}</p>
                    </div>
                  </motion.div>
                ))}
              </div>
            </motion.div>

            <motion.div
              initial={{ opacity: 0, x: 40 }}
              whileInView={{ opacity: 1, x: 0 }}
              viewport={{ once: true }}
              transition={{ duration: 0.8 }}
              className="relative"
            >
              <div className="relative mb-8">
                <img
                  src="https://images.unsplash.com/photo-1764114235896-034c8772de01?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&q=80&w=1200"
                  alt="Manufacturing"
                  className="w-full h-[600px] object-cover"
                />
                <div className="absolute -bottom-8 -left-8 bg-white p-8 shadow-lg max-w-xs">
                  <div className="text-5xl font-bold mb-2">25+</div>
                  <div className="text-gray-600">年行业经验</div>
                </div>
              </div>

              <div className="grid grid-cols-3 gap-4 pt-8">
                <div className="text-center p-4 border border-gray-200">
                  <Award size={32} className="mx-auto mb-2 text-gray-400" />
                  <div className="text-xs text-gray-600">ISO 9001</div>
                </div>
                <div className="text-center p-4 border border-gray-200">
                  <Award size={32} className="mx-auto mb-2 text-gray-400" />
                  <div className="text-xs text-gray-600">IATF 16949</div>
                </div>
                <div className="text-center p-4 border border-gray-200">
                  <Award size={32} className="mx-auto mb-2 text-gray-400" />
                  <div className="text-xs text-gray-600">CE认证</div>
                </div>
              </div>
            </motion.div>
          </div>
        </div>
      </section>

      {/* Global Presence */}
      <section id="global" className="py-32 bg-black text-white">
        <div className="max-w-7xl mx-auto px-6 lg:px-8">
          <motion.div
            initial={{ opacity: 0, y: 40 }}
            whileInView={{ opacity: 1, y: 0 }}
            viewport={{ once: true }}
            transition={{ duration: 0.6 }}
            className="text-center mb-20"
          >
            <div className="inline-block px-4 py-2 bg-white text-black text-sm tracking-widest mb-6">
              FROM CHINA TO THE WORLD
            </div>
            <h2 className="text-5xl md:text-6xl font-bold mb-6">立足中国 · 服务全球</h2>
            <p className="text-xl text-gray-400 max-w-3xl mx-auto">
              亚洲、欧洲、北美15个生产基地，24小时响应全球需求
            </p>
          </motion.div>

          <div className="grid grid-cols-2 md:grid-cols-4 gap-8 mb-20">
            {[
              { number: "25+", label: "年行业经验" },
              { number: "50+", label: "服务国家" },
              { number: "3000+", label: "全球客户" },
              { number: "5000+", label: "SKU产品" }
            ].map((stat, idx) => (
              <motion.div
                key={idx}
                initial={{ opacity: 0, scale: 0.8 }}
                whileInView={{ opacity: 1, scale: 1 }}
                viewport={{ once: true }}
                transition={{ duration: 0.6, delay: idx * 0.1 }}
                className="text-center"
              >
                <div className="text-5xl md:text-6xl font-bold mb-2">{stat.number}</div>
                <div className="text-gray-400">{stat.label}</div>
              </motion.div>
            ))}
          </div>

          <motion.div
            initial={{ opacity: 0, y: 40 }}
            whileInView={{ opacity: 1, y: 0 }}
            viewport={{ once: true }}
            transition={{ duration: 0.8 }}
            className="relative h-96 overflow-hidden"
          >
            <img
              src="https://images.unsplash.com/photo-1758873263428-f4b2edb45fe1?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&q=80&w=1600"
              alt="Global"
              className="w-full h-full object-cover opacity-30"
            />
            <div className="absolute inset-0 flex items-center justify-center">
              <div className="text-center max-w-3xl">
                <h3 className="text-3xl md:text-5xl font-bold mb-4">中国智造 · 世界品质</h3>
                <p className="text-xl text-gray-300 mb-8">与全球顶级汽车品牌共同选择的零部件合作伙伴</p>
                <div className="flex gap-4 justify-center">
                  <button className="px-8 py-4 bg-white text-black hover:bg-gray-100 transition-colors">
                    获取报价
                  </button>
                  <button className="px-8 py-4 border-2 border-white text-white hover:bg-white hover:text-black transition-all">
                    下载产品目录
                  </button>
                </div>
              </div>
            </div>
          </motion.div>
        </div>
      </section>

      {/* Footer */}
      <footer className="bg-gray-900 text-gray-400 py-16">
        <div className="max-w-7xl mx-auto px-6 lg:px-8">
          <div className="grid grid-cols-1 md:grid-cols-4 gap-12 mb-12">
            <div>
              <div className="text-white text-2xl font-semibold mb-2">盛骏</div>
              <div className="text-sm text-gray-500 mb-4">SHENGJUN</div>
              <p className="text-sm">中国顶级汽车零部件供应商</p>
              <p className="text-xs text-gray-500 mt-2">ISO/IATF双认证企业</p>
            </div>
            <div>
              <h4 className="text-white font-semibold mb-4">产品系列</h4>
              <ul className="space-y-2 text-sm">
                <li><a href="#" className="hover:text-white transition-colors">发动机系统</a></li>
                <li><a href="#" className="hover:text-white transition-colors">底盘系统</a></li>
                <li><a href="#" className="hover:text-white transition-colors">传动系统</a></li>
                <li><a href="#" className="hover:text-white transition-colors">电气系统</a></li>
              </ul>
            </div>
            <div>
              <h4 className="text-white font-semibold mb-4">关于盛骏</h4>
              <ul className="space-y-2 text-sm">
                <li><a href="#" className="hover:text-white transition-colors">公司简介</a></li>
                <li><a href="#" className="hover:text-white transition-colors">质量认证</a></li>
                <li><a href="#" className="hover:text-white transition-colors">全球布局</a></li>
                <li><a href="#" className="hover:text-white transition-colors">加入我们</a></li>
              </ul>
            </div>
            <div>
              <h4 className="text-white font-semibold mb-4">联系我们</h4>
              <ul className="space-y-2 text-sm">
                <li>总部: +86 (21) 6888 8888</li>
                <li>邮箱: global@shengjun.com</li>
                <li>中国 · 上海</li>
                <li className="pt-2">
                  <a href="#" className="text-white hover:underline">获取产品目录 →</a>
                </li>
              </ul>
            </div>
          </div>
          <div className="border-t border-gray-800 pt-8 flex flex-col md:flex-row justify-between items-center text-sm gap-4">
            <p>&copy; 2026 盛骏汽车零部件 SHENGJUN. 保留所有权利.</p>
            <div className="flex gap-6">
              <a href="#" className="hover:text-white transition-colors">隐私政策</a>
              <a href="#" className="hover:text-white transition-colors">使用条款</a>
              <a href="#" className="hover:text-white transition-colors">质量保证</a>
            </div>
          </div>
        </div>
      </footer>
    </div>
  );
}