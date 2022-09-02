import { Routes, Route } from "react-router-dom"
import './App.css';
import Home from './components/home/Home'
import LoginForm from './components/login-form/LoginForm'
import Unauthorized from './components/unauthorized/Unauthorized'
import Notfound from './components/not-found/NotFound'
import ManagerTicketList from './components/manager-ticket-list/ManagerTicketList'

import Layout from "./Layout";

function App() {

  return (
    <Routes >
      <Route path="/" element={<Layout />}>
        {/*Public routes*/}
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<LoginForm />} />
        <Route path='/unauthorized' element={<Unauthorized />} />
        
        <Route path='*' element={<Notfound />} />
        {/*Staff routes*/}

        {/*Manager routes*/}
        <Route path='manage-tickets' element={<ManagerTicketList />} />
      </Route>
    </Routes>
  );
}

export default App;
