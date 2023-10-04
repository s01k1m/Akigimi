type KeypadEntries = {
    entered: Boolean;
  };
  
  export default function Circle({ entered }: KeypadEntries) {
    return (
      <div className="mx-[11px]">
        <svg
          className={entered ? "entered" : null}
          xmlns="http://www.w3.org/2000/svg"
          width="24"
          height="24"
          viewBox="0 0 24 24"
          fill="none"
        >
          <circle cx="12" cy="12" r="12" fill={entered ? "" : "#D9D9D9"} />
        </svg>
      </div>
    );
  }