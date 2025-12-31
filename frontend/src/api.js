const BASE = '/api'

export async function fetchPacientes() {
  try {
    const res = await fetch(`${BASE}/pacientes`)
    if (!res.ok) return null
    return await res.json()
  } catch (e) {
    console.error(e)
    return null
  }
}

export async function createPaciente(payload) {
  try {
    const res = await fetch(`${BASE}/paciente`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    })
    return await res.json()
  } catch (e) {
    console.error(e)
    return { error: e.message }
  }
}
