// server.js - HTTPS Weather App Server
const https = require('https');
const http = require('http');
const fs = require('fs');
const path = require('path');

// SSL options using the certificates you created
const sslOptions = {
    key: fs.readFileSync('./localhost-key.pem'),
    cert: fs.readFileSync('./localhost.pem')
};

// Create HTTPS server
const server = https.createServer(sslOptions, async (req, res) => {
    // Set CORS headers
    res.setHeader('Access-Control-Allow-Origin', '*');
    res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS');
    res.setHeader('Access-Control-Allow-Headers', 'Content-Type');
    
    // Handle preflight requests
    if (req.method === 'OPTIONS') {
        res.writeHead(200);
        res.end();
        return;
    }
    
    console.log(`${new Date().toISOString()} - ${req.method} ${req.url}`);
    
    // Serve the main HTML page
    if (req.url === '/' || req.url === '/index.html') {
        try {
            let html = fs.readFileSync('./index.html', 'utf8');
            res.writeHead(200, { 'Content-Type': 'text/html' });
            res.end(html);
        } catch (error) {
            res.writeHead(500, { 'Content-Type': 'text/plain' });
            res.end('Error loading index.html');
        }
        return;
    }
    
    // Proxy API requests to your Spring Boot backend
    if (req.url.startsWith('/api/weather/')) {
        const location = decodeURIComponent(req.url.split('/api/weather/')[1]);
        
        console.log(`Proxying weather request for: ${location}`);
        
        const options = {
            hostname: 'localhost',
            port: 8080,
            path: `/api/weather/city/${encodeURIComponent(location)}`,
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        };
        
        const proxyReq = http.request(options, (proxyRes) => {
            let data = '';
            
            proxyRes.on('data', (chunk) => {
                data += chunk;
            });
            
            proxyRes.on('end', () => {
                res.writeHead(proxyRes.statusCode, {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                });
                res.end(data);
            });
        });
        
        proxyReq.on('error', (error) => {
            console.error('Proxy error:', error);
            res.writeHead(500, { 
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            });
            res.end(JSON.stringify({ 
                error: 'Failed to connect to weather backend',
                details: error.message 
            }));
        });
        
        proxyReq.end();
        return;
    }
    
    // 404 for other routes
    res.writeHead(404, { 'Content-Type': 'text/plain' });
    res.end('Not found');
});

const PORT = 3000;
server.listen(PORT, () => {
    console.log('\n✨ ====================================');
    console.log('🌦️  WeatherVerse Pro - HTTPS Edition');
    console.log('✨ ====================================');
    console.log(`✅ HTTPS Server running on: https://localhost:${PORT}`);
    console.log(`🔐 Using SSL certificates: localhost.pem & localhost-key.pem`);
    console.log(`📡 Proxying to backend: http://localhost:8080`);
    console.log(`\n⚠️  Note: Browser will show security warning`);
    console.log(`   Click "Advanced" → "Proceed to localhost"`);
    console.log('✨ ====================================\n');
});