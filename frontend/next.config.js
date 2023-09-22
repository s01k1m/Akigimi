/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: false,
  swcMinify: true,
  compiler: {
    styledComponents: true,
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
