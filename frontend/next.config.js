/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: false,
  swcMinify: true,
  compiler: {
    styledComponents: true,
  },
  
  // 외부 이미지 불러오기
  // https://akgimi-bucket.s3.ap-northeast-2.amazonaws.com/images/feed9999.png2023-09-21T14:56:16.383037500
  images: {
    remotePatterns: [
      {
        protocol: "https",
        hostname: `akgimi-bucket.s3.ap-northeast-2.amazonaws.com` 
      }
    ]
  },

  async rewrites() {
    return [
      {
        source: "/api/:path*",
        destination: `http://25.4.167.82:8080/:path*`,
      },
    ];
  },
};

module.exports = nextConfig;
