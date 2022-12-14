import React from "react"
import { Link } from "react-router-dom";

import './Navbar.css'
import fox_icon from '../../img/fox-icon.png'


function Navbar() {
    return (
        <nav>
            <Link className='nav-img' to='/'><img src={fox_icon} alt='company logo' /></Link>
            <p>
                Fox Logistics
            </p>
            <div id='nav-button-list'>
                <Link className="nav-button" to="/login">Login</Link>
                <Link className="nav-button" to="/manage-tickets">Manage Tickets</Link>
                {/* <li className='nav-button'>New Ticket</li>
                <li className='nav-button'>Submitted Tickets</li>
                <li className='nav-button'>Manage Tickets</li>
                <li className='nav-button'>New Employee</li>
                <li className='nav-button'>Manage Employee</li>
                <li className='nav-button'>Logout</li> */}
            </div>
        </nav>
    )
}

export default Navbar