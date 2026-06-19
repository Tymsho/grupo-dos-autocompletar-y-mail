import { useState, useEffect } from 'react';
import useDebounce from '../hooks/useDebounce';

/**
 * Componente que muestra una barra de búsqueda con autocompletado.
 * @param {Object} props - Propiedades del componente.
 * @param {Function} props.onSelectClient - Función a ejecutar al seleccionar un cliente.
 */
function SearchAutocomplete({ onSelectClient }) {
  const [query, setQuery] = useState('');
  const [results, setResults] = useState([]);
  const [isSearching, setIsSearching] = useState(false);
  const [isOpen, setIsOpen] = useState(false);

  // Hook personalizado para retrasar la búsqueda (debounce) y no saturar el backend
  const debouncedQuery = useDebounce(query, 300);

  // Efecto que se dispara cada vez que cambia la búsqueda retrasada
  useEffect(() => {
    const fetchClients = async () => {
      setIsSearching(true);
      try {
        const response = await fetch(`http://localhost:8081/api/clients/search?q=${debouncedQuery}`);
        if (!response.ok) throw new Error("Error en la respuesta");
        const data = await response.json();
        setResults(data);
      } catch (error) {
        console.error("Error fetching clients:", error);
      } finally {
        setIsSearching(false);
      }
    };

    fetchClients();
  }, [debouncedQuery]);

  // Manejador para cuando el usuario hace clic en un resultado de la lista
  const handleSelect = (client) => {
    onSelectClient(client); // Envía el cliente seleccionado al componente padre (App.jsx)
    setQuery(''); // Limpia el input tras la selección
    setIsOpen(false); // Cierra el menú desplegable
  };

  return (
    <div className="autocomplete-container">
      <div style={{position: 'relative'}}>
        <input
          type="text"
          className="search-input"
          placeholder="Buscar clientes por nombre..."
          value={query}
          onChange={(e) => setQuery(e.target.value)}
          onFocus={() => setIsOpen(true)}
        />
        {/* Indicador visual animado mientras se espera la respuesta del servidor */}
        {isSearching && (
          <div className="searching-indicator">
            <div className="dot dot-1"></div>
            <div className="dot dot-2"></div>
            <div className="dot"></div>
          </div>
        )}
      </div>

      {/* Lista desplegable de resultados, visible solo si está abierta y hay clientes */}
      {isOpen && results.length > 0 && (
        <ul className="dropdown-list">
          {results.map((client) => (
            <li
              key={client.id}
              onClick={() => handleSelect(client)}
              className="dropdown-item"
            >
              <div>
                <p className="item-name">{client.name}</p>
                <p className="item-email">{client.email}</p>
              </div>
              <span className={`status-badge ${client.status === 'ACTIVE' ? 'status-active' : 'status-inactive'}`}>
                {client.status}
              </span>
            </li>
          ))}
        </ul>
      )}

      {/* Mensaje cuando la búsqueda no arroja resultados */}
      {isOpen && !isSearching && results.length === 0 && (
        <div className="no-results">
          No se encontraron clientes.
        </div>
      )}
    </div>
  );
}

export default SearchAutocomplete;
