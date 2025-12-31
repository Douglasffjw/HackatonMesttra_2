import React, { useEffect, useState } from 'react'
import { fetchPacientes, createPaciente } from './api'

export default function App() {
  const [pacientes, setPacientes] = useState([])
  const [form, setForm] = useState({ nome: '', cpf: '', sexo: 'M', nascimento: '' })
  const [message, setMessage] = useState('')

  useEffect(() => {
    load()
  }, [])

  async function load() {
    const data = await fetchPacientes()
    setPacientes(data || [])
  }

  async function handleSubmit(e) {
    e.preventDefault()
    setMessage('')
    const res = await createPaciente(form)
    if (res && res.message) {
      setMessage(res.message)
      setForm({ nome: '', cpf: '', sexo: 'M', nascimento: '' })
      load()
    } else if (res && res.error) {
      setMessage('Erro: ' + res.error)
    } else {
      setMessage('Erro desconhecido')
    }
  }

  return (
    <div className="container">
      <h1>Gerenciador de Vacinas</h1>

      <section>
        <h2>Novo Paciente</h2>
        <form onSubmit={handleSubmit} className="form">
          <input placeholder="Nome" value={form.nome} onChange={e => setForm({ ...form, nome: e.target.value })} required />
          <input placeholder="CPF" value={form.cpf} onChange={e => setForm({ ...form, cpf: e.target.value })} required />
          <select value={form.sexo} onChange={e => setForm({ ...form, sexo: e.target.value })}>
            <option value="M">M</option>
            <option value="F">F</option>
          </select>
          <input type="date" value={form.nascimento} onChange={e => setForm({ ...form, nascimento: e.target.value })} required />
          <button type="submit">Criar</button>
        </form>
        {message && <p className="message">{message}</p>}
      </section>

      <section>
        <h2>Pacientes</h2>
        <button onClick={load}>Atualizar</button>
        <ul>
          {pacientes.map(p => (
            <li key={p.id}>{p.nome} — {p.cpf} — {p.sexo} — {p.dataNascimento}</li>
          ))}
        </ul>
      </section>
    </div>
  )
}
