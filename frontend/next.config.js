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
        destination: `http://25.4.167.82:8080/:path*`,
      },
    ];
  },
};

module.exports = nextConfig;
