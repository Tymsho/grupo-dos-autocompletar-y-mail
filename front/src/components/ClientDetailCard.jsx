import { useState } from 'react';

function ClientDetailCard({ client, onClearSelection }) {
  const [subject, setSubject] = useState('');
  const [body, setBody] = useState('');
  const [isSending, setIsSending] = useState(false);
  const [successMessage, setSuccessMessage] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  if (!client) return null;

  const handleSendNotification = async (e) => {
    e.preventDefault();
    setIsSending(true);
    setSuccessMessage('');
    setErrorMessage('');

    try {
      const response = await fetch(`http://localhost:8081/api/clients/${client.id}/notify`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ subject, body }),
      });

      if (response.status === 202 || response.ok) {
        setSuccessMessage('Notificación en proceso de envío');
        setSubject('');
        setBody('');
      } else {
        throw new Error('Error al enviar la notificación');
      }
    } catch (error) {
      setErrorMessage(error.message);
    } finally {
      setIsSending(false);
    }
  };

  return (
    <div className="card-container">
      <div className="card-header">
        <button 
          onClick={onClearSelection}
          className="close-btn"
          aria-label="Cerrar"
        >
          <svg className="close-icon" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
          </svg>
        </button>
        <div className="header-content">
          <div className="avatar">
            {client.name.charAt(0).toUpperCase()}
          </div>
          <div>
            <h2 className="client-name">{client.name}</h2>
            <p className="client-email">{client.email}</p>
          </div>
        </div>
      </div>

      <div className="card-body">
        <div className="status-row">
          <span className="status-label">Estado de cuenta</span>
          <span className={`status-badge ${client.status === 'ACTIVE' ? 'status-active' : 'status-inactive'}`}>
            {client.status}
          </span>
        </div>

        <hr className="divider" />

        <h3 className="form-title">Enviar Notificación</h3>
        
        <form onSubmit={handleSendNotification}>
          <div className="form-group">
            <label className="form-label">Asunto</label>
            <input
              type="text"
              required
              value={subject}
              onChange={(e) => setSubject(e.target.value)}
              className="form-input"
              placeholder="Recordatorio de pago..."
            />
          </div>
          
          <div className="form-group">
            <label className="form-label">Mensaje</label>
            <textarea
              required
              rows="4"
              value={body}
              onChange={(e) => setBody(e.target.value)}
              className="form-textarea"
              placeholder="Escribe el cuerpo del correo aquí..."
            ></textarea>
          </div>

          <button
            type="submit"
            disabled={isSending}
            className="submit-btn"
          >
            {isSending ? (
              <>
                <svg className="spinner" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                  <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"></circle>
                  <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                </svg>
                <span>Enviando...</span>
              </>
            ) : (
              <span>Enviar Notificación</span>
            )}
          </button>
        </form>

        {successMessage && (
          <div className="message-box success-box">
            <svg className="message-icon" fill="currentColor" viewBox="0 0 20 20">
              <path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clipRule="evenodd"/>
            </svg>
            {successMessage}
          </div>
        )}

        {errorMessage && (
          <div className="message-box error-box">
            {errorMessage}
          </div>
        )}
      </div>
    </div>
  );
}

export default ClientDetailCard;
