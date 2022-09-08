import React, { createContext, useState } from "react"

export interface AuthProviderProps {
    children: React.ReactNode
}

export interface AuthProviderContext {
    auth: {jwt:string , employee: object, role:string},
    setAuth: (prevState: {jwt: string, employee: object, role: string}) => void
}

const defaultContext: AuthProviderContext = {
    auth: {jwt: '', employee: {}, role: ''},
    setAuth: () => {}
}

export const AuthContext = createContext<AuthProviderContext>(defaultContext)

const {Provider} = AuthContext

const AuthProvider: React.FC<AuthProviderProps> = ({children}) => {
    const [auth, setAuth] = useState({jwt: '', employee: {}, role: ''})

    // //If data exists, load it from local storage
    // useEffect(() => {
    //     const storedAuth = localStorage.getItem('auth')
    //     console.log('Trying to restore auth data...')
    //     if(storedAuth) {
    //         setAuth(JSON.parse(storedAuth))
    //         console.log(`Restoration success. Jwt read is now: ${auth.jwt}`)
    //     } else {
    //         console.log('Restoration failed')
    //     }
    // }, [])

    // //Save user login to persist between page refresh (such as when new work is saved and npm refreshes the page)
    // useEffect(() => {
    //     localStorage.setItem('auth', JSON.stringify(auth))
    //     console.log('Saved auth info to local storage')
    // }, [auth])

    return (
        <Provider value={{auth, setAuth}}>
            {children}
        </Provider>
    )
}

export default AuthProvider