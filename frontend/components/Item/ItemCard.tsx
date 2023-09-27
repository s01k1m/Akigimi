import ItemButton from "./ItemButton";
import axios from "axios";
export interface ItemProps {
  challengeId: number;
  productId: number;
  inProgress: boolean;
  achievementState: boolean;
  isPostScriptCreated: boolean;
}

const ItemCard = ({
  challengeId,
  productId,
  inProgress,
  achievementState,
  isPostScriptCreated,
}: ItemProps) => {
  function calculateStatus(inProgress, achievementState, isPostScriptCreated) {
    if (inProgress) {
      // 진행중인 챌린지는
      return "ongoing";
    } else if (!inProgress && achievementState && isPostScriptCreated) {
      // 성공하고 후기 있는 챌린지
      return "success";
    } else if (!inProgress && achievementState && isPostScriptCreated) {
      // 성공하고 후기 없는 챌린지
      return "successbut";
    } else if (!inProgress && !achievementState) {
      // 실패한 챌린지
      return "fail";
    }
  }
  let status = calculateStatus(
    inProgress,
    achievementState,
    isPostScriptCreated
  );
  async function getItemInfo(productId) {
    let token = window.localStorage.getItem("access_token");
    await axios.get("/api/");
  }
  return (
    <div>
      <ItemButton status={status}></ItemButton>
    </div>
  );
};

export default ItemCard;
