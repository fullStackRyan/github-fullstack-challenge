import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import RepoDetailsCard from "../components/RepoDetailsCard";
import { RepositoryOverview } from "../types/Types";
import { ClimbingBoxLoader } from "react-spinners";

type Params = {
  id: string;
  repoName: string;
};

function DetailsPage(): JSX.Element {
  const { id, repoName } = useParams<Params>();
  const [repoDetails, setRepoDetails] = useState<RepositoryOverview | null>(
    null
  );
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    fetchRepoDetails();
  }, [id, repoName]);

  const fetchRepoDetails = async (): Promise<void> => {
    try {
      // const response = await axios.get<RepositoryOverview>(
      //   `/github/${id}/${repoName}`
      // );
      // setRepoDetails(response.data);
      setRepoDetails({
        avatar: "https://avatars.githubusercontent.com/u/59806496?v=4",
        lastUpdated: new Date("02/12/1990"),
        author: "fullstackryan",
        programmingLanguage: "Vim",
      });
    } catch (error) {
      setError("Error fetching repos.");
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <div className="flex justify-center items-center min-h-screen">
        <ClimbingBoxLoader color="#36d7b7" loading={loading} size={40} />
      </div>
    );
  }
  if (error) return <div>Error: {error}</div>;

  return (
    <div className="bg-gradient-to-r from-yellow-400 via-red-500 to-pink-500 text-white">
      {repoDetails && <RepoDetailsCard overview={repoDetails} />}
    </div>
  );
}

export default DetailsPage;
