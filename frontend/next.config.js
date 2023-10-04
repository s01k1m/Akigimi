/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: false,
  swcMinify: true,
  compiler: {
    styledComponents: true,
  },
  
  // 외부 이미지 불러오기
  images: {
    remotePatterns: [
      {
        protocol: "https",
        hostname: `akgimi-bucket.s3.ap-northeast-2.amazonaws.com` 
      },
      {
        protocol: "http",
        hostname: `k.kakaocdn.net` 
      },
      {
        protocol: "https",
        hostname: `postfiles.pstatic.net` 
      }
    ]
  },

  async rewrites() {
    return [
      {
        source: "/api/:path*",
        destination: `http://akgimi.ddns.net/api/:path*`,
      },
    ];
  },
};

module.exports = nextConfig;
