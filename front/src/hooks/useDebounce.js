import { useState, useEffect } from 'react';

function useDebounce(value, delay) {
  const [debouncedValue, setDebouncedValue] = useState(value);

  useEffect(() => {
    // Establecer un timeout para actualizar el valor debounced después del retraso
    const handler = setTimeout(() => {
      setDebouncedValue(value);
    }, delay);

    // Cancelar el timeout si el valor cambia antes de que termine el retraso (cleanup)
    return () => {
      clearTimeout(handler);
    };
  }, [value, delay]); // Se ejecuta de nuevo si value o delay cambian

  return debouncedValue;
}

export default useDebounce;
