import React, { ChangeEvent, FormEvent, useEffect, useContext } from "react"

import {AuthContext} from "../../context/AuthProvider"
import AuthRequest from "../../models/login/AuthRequest"
import axios from "../../api/axios"
import './LoginForm.css'

function LoginForm() {

    const errTxt = "Invalid username/password combo"
    const login_url = 'auth/login'

    const {setAuth} = useContext(AuthContext)
    const [loginCreds, setLoginCreds] = React.useState(new AuthRequest("", ""))
    const [errMsg, setErrMsg] = React.useState('')

    useEffect(() => {
        setErrMsg('')
    }, [loginCreds])

    function handleInput(event: ChangeEvent) {

        const target: EventTarget = event.target
        const inputEle: HTMLInputElement = target as HTMLInputElement
        
        switch(inputEle.id) {
            case 'username-textbox':
                setLoginCreds(prevLogin => new AuthRequest(inputEle.value, prevLogin.password))
                break;
            case 'password-textbox':
                setLoginCreds(prevLogin => new AuthRequest(prevLogin.username, inputEle.value))
        }
    }

    async function handleSubmit(event: FormEvent) {
        event.preventDefault()

        try {
            let jwt: string
            let employee: object
            let role: string

            const res = await axios.post(
                login_url, 
                JSON.stringify(loginCreds),
                {
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }
            )

            jwt = await res.data?.jwt
            employee = await res.data?.employee
            role = await res.data?.employee?.role

            setAuth({jwt, employee, role})
        } catch(err: any) {
            if( ! err?.response) setErrMsg("No server response")
            else if(err.response?.status === 400) setErrMsg('Missing username or password')
            else if(err.response?.status === 401) setErrMsg(errTxt)
        }
    }

    return (
        <form onSubmit={handleSubmit}>
            {
                errMsg !== '' && 
                <label className='err-box'>
                {errMsg}
                </label>
            }
            <label htmlFor='username-textbox'>Username:</label>
            <input id='username-textbox' name='username-textbox' type='text' value={loginCreds.username} onChange={handleInput}></input>
            <label htmlFor='password-textbox'>Password:</label>
            <input id='password-textbox' name='password-textbox' type='password' value={loginCreds.password} onChange={handleInput}></input>
            <button>Log in</button>
        </form>
    )
}

export default LoginForm