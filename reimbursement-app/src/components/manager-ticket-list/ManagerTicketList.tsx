import React, { useContext, useEffect, useState } from "react"

import './ManagerTicketList.css'
import axios, {TicketDataType} from '../../api/axios'
import {AuthContext} from '../../context/AuthProvider'
import TicketList from "../ticket-list/TicketList"

function ManagerTicketList() {

    const {auth} = useContext(AuthContext)
    const [data, setData] = useState<TicketDataType>({tickets: [], totalItems: 0, totalPages: 0, currentPage: 0})
    
    async function getPageOfTickets(page: number) {
        try {
            const res = await axios.get(
                `tickets/${page}/10`,
                {
                    headers: {
                        'Authorization': `Bearer ${auth.jwt}`
                    }
                }
            )

            if(res.status !== 200) throw new Error(`Unable to retreive data: ${res.status}`)

            setData(res.data)
        } catch(err) {
        
        }
    }

    //I love that the linter says to remove the empty [], but it's VERY much required
    //eslint-disable-next-line
    useEffect(() => {getPageOfTickets(0)}, [])
    
    return (
        <TicketList tickets={data.tickets.map(ticket => {
            return {...ticket, key: ticket.id}
        })}/>
    )
}

export default ManagerTicketList