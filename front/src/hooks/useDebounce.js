import { useState, useEffect } from 'react';

/**
 * Custom Hook para retrasar la actualización de un valor (debounce).
 * Útil para evitar peticiones excesivas a una API mientras el usuario escribe.
 * 
 * @param {any} value - El valor actual que queremos retrasar (ej. texto del input).
 * @param {number} delay - El tiempo en milisegundos a esperar antes de actualizar.
 * @returns {any} El valor debanceado tras esperar el tiempo indicado.
 */
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
