import { BrowserRouter, Routes, Route } from "react-router-dom"
import './App.css';
import Home from './components/home/Home'
import LoginForm from './components/login-form/LoginForm'

import Navbar from './components/navbar/Navbar'

function App() {

  return (
    <div id="app-content">
      <BrowserRouter>
        <Navbar />
        <main>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<LoginForm />} />
          </Routes>
        </main>
      </BrowserRouter>
    </div>
  );
}

export default App;
