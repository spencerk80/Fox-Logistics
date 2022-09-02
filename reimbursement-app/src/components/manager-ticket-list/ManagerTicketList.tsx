import React, { useContext, useEffect, useState } from "react"

import './ManagerTicketList.css'
import axios from '../../api/axios'
import {AuthContext} from '../../context/AuthProvider'

function ManagerTicketList() {

    const {auth} = useContext(AuthContext)
    const [data, setData] = useState({})
    
    async function getPageOfTickets(page: number) {
        try {
            const res = await axios.get(
                `tickets/${page}/10`,
                {
                    headers: {
                        'Authorization': 'Bearer' + auth.jwt
                    }
                }
            )

            if(res.status !== 200) throw new Error(`Unable to retreive data: ${res.status}`)

            setData(res.data)
        } catch(err) {
        
        }
    }

    useEffect(() => {getPageOfTickets(0)})
    
    return (
        <p>
            {JSON.stringify(data)}
        </p>
    )
}

export default ManagerTicketList