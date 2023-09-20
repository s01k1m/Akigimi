import Image from "next/image";

interface FollowingProps {
  total: number;
}

export default function FollowingButton({ total }: FollowingProps) {
  return (
    <div className="text-center text-[17px]">
      <div>
        <div>{total}</div>
        <div>팔로워</div>
      </div>
    </div>
  );
}
