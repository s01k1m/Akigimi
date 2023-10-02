import ItemCard from "@/components/Item/ItemCard";
import { ItemProps } from "@/components/Item/ItemCard";

const fake: ItemProps[] = [
  {
    challengeId: 0,
    productId: 1,
    inProgress: true,
    achievementState: false,
    isPostScriptCreated: false,
  },
  {
    challengeId: 1,
    productId: 2,
    inProgress: false,
    achievementState: true,
    isPostScriptCreated: false,
  },
  {
    challengeId: 2,
    productId: 2,
    inProgress: false,
    achievementState: false,
    isPostScriptCreated: false,
  },
  {
    challengeId: 1,
    productId: 2,
    inProgress: false,
    achievementState: true,
    isPostScriptCreated: true,
  },
];
const MyItems = async () => {
  return (
    <>
      <div>나의 챌린지 기록</div>
      {/* build를 위한 주석입니다 */}
      {/* {<ItemCard></ItemCard>} */}
    </>
  );
};

export default MyItems;
