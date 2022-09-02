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

    return (
        <Provider value={{auth, setAuth}}>
            {children}
        </Provider>
    )
}

export default AuthProvider