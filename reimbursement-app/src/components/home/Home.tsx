import './Home.css'

function Home() {
    return (
        <article>
            <h1>
                Welcome to the Fox Logistics reimbursement ticket system
            </h1>
            <h2>
                Staff
            </h2>
            <p>
                Staff need to request a manager to set them up with an account. Once they have their account set up,
                staff can login and manage their reimbursement tickets. Create a new ticket for an expense or view
                previously submitted tickets to check on their status.
            </p>
            <h2>
                Managers
            </h2>
            <p>
                New managers need to see an existing manager or IT to set up their account. Once they have their account
                set up, manager can log in to manage tickets. View all tickets submitted, filter by status and approve or
                deny pending tickets.
            </p>

        </article>
    )
}

export default Home