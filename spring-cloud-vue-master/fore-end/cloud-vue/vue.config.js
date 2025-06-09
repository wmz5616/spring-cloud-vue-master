/* eslint-disable */
module.exports = {
    devServer: {
        proxy: {
            '/api': {
                target: 'http://localhost:8880',
                changeOrigin: true
            }
        }
    }
};
