import { useState, useEffect } from 'react';
import useDebounce from '../hooks/useDebounce';

function SearchAutocomplete({ onSelectClient }) {
  const [query, setQuery] = useState('');
  const [results, setResults] = useState([]);
  const [isSearching, setIsSearching] = useState(false);
  const [isOpen, setIsOpen] = useState(false);

  const debouncedQuery = useDebounce(query, 300);

  useEffect(() => {
    // Si el query está vacío, no buscamos (o podemos buscar con q="")
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

  const handleSelect = (client) => {
    onSelectClient(client);
    setQuery(''); // Limpiamos el input o lo dejamos con el nombre
    setIsOpen(false);
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
        {isSearching && (
          <div className="searching-indicator">
            <div className="dot dot-1"></div>
            <div className="dot dot-2"></div>
            <div className="dot"></div>
          </div>
        )}
      </div>

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

      {isOpen && !isSearching && results.length === 0 && (
        <div className="no-results">
          No se encontraron clientes.
        </div>
      )}
    </div>
  );
}

export default SearchAutocomplete;
