import { useParams } from "react-router-dom";

function DetailsPage() {
  const { id } = useParams();

  return <div>{id}</div>;
}

export default DetailsPage;
