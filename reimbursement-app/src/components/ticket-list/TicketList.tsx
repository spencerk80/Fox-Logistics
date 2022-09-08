import React from "react"
import Ticket, { TicketProps } from "../ticket/Ticket"

import './TicketList.css'

interface TicketListProps {
    tickets: Array<TicketProps>
}

function TicketList(props: TicketListProps) {
    return(
        <article className='ticket-list'>
            {props.tickets.map(ticket => <Ticket {...ticket}/>)}
        </article>
    )
}

export default TicketList