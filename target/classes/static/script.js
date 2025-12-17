// API Base URL
const API_BASE = 'http://localhost:8080/api';

// Store user data
let currentUser = null;

// Show/Hide elements
function showElement(id) {
    document.getElementById(id).style.display = 'block';
}

function hideElement(id) {
    document.getElementById(id).style.display = 'none';
}

// Show message
function showMessage(type, text, elementId = 'message') {
    const messageDiv = document.getElementById(elementId);
    messageDiv.className = `message ${type}`;
    messageDiv.textContent = text;
    messageDiv.style.display = 'block';
    
    setTimeout(() => {
        messageDiv.style.display = 'none';
    }, 5000);
}

// API Helper Functions
async function makeRequest(url, method, data = null) {
    const options = {
        method: method,
        headers: {
            'Content-Type': 'application/json'
        }
    };
    
    if (data) {
        options.body = JSON.stringify(data);
    }
    
    try {
        const response = await fetch(url, options);
        return await response.json();
    } catch (error) {
        console.error('Request failed:', error);
        return { success: false, message: 'Network error' };
    }
}

// Check if user is logged in
function checkAuth() {
    const userData = localStorage.getItem('bankUser');
    if (userData) {
        currentUser = JSON.parse(userData);
        return true;
    }
    return false;
}

// Logout
function logout() {
    localStorage.removeItem('bankUser');
    currentUser = null;
    window.location.href = 'index.html';
}

// Redirect based on role
function redirectBasedOnRole() {
    if (currentUser) {
        if (currentUser.role === 'ADMIN') {
            window.location.href = 'admin.html';
        } else {
            window.location.href = 'customer.html';
        }
    }
}