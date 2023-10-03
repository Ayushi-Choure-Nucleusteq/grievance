import { useState, useEffect } from "react";

const Popup = (props) => {
  const [display, setDisplay] = useState(true);
  useEffect(() => {
    const timer = setTimeout(() => setDisplay(false), 5000);
    return () => clearTimeout(timer); // to clear the timer when the component unmounts
  }, []);

  // console.log(props.message)
  // console.log(display)
  return (
    <>
      <div
        style={{
          position: "fixed",
          display: display ? "inline-block" : "none",
          top: "100px",
          right: "80px",
          backgroundColor: props.color,
          padding: "15px 20px",
          borderRadius: "10px",
          boxShadow: "0px 4px 10px rgba(0, 0, 0, 0.1)",
          fontSize: "1.1rem",
          letterSpacing: "0.5px",
          transition: "all 0.3s ease",
          zIndex: 1000,
          width: "auto",
          maxWidth: "300px",
          overflowWrap: "break-word",
          textAlign: "center",
        }}
      >
        {props.message}
      </div>
    </>
  );
};

export default Popup;
