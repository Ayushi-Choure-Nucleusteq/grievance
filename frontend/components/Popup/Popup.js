import { useState, useEffect } from "react";
import "./Popup.css";

const Popup = (props) => {
  const [display, setDisplay] = useState(true);
  useEffect(() => {
    const timer = setTimeout(() => setDisplay(false), 5000);
    return () => clearTimeout(timer);
  }, []);

  return (
      <div
        className={`popup ${display ? 'display' : ''}`}
        style={{ backgroundColor: props.color }}
      >
        {props.message}
      </div>
  );
};

export default Popup;
