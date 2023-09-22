import Image from "next/image";

interface FollowerProps {
  total: number;
}

export default function FollowerButton({ total }: FollowerProps) {
  return (
    <div className="text-center text-[17px]">
      <div>
        <div>{total}</div>
        <div>팔로워</div>
      </div>
    </div>
  );
}
