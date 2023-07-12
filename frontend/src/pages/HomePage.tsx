import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";
import { Repository } from "../types/Repository";
import RepoOverviewCard from "../components/RepoOverviewCard";
import SearchBox from "../components/SearchBox";

function HomePage(): JSX.Element {
  const [repos, setRepos] = useState<Repository[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [searchValue, setSearchValue] = useState<string>("");

  useEffect(() => {
    fetchRepos();
  }, [searchValue]);

  const fetchRepos = async (): Promise<void> => {
    try {
      const response = await axios.get(
        `/github?name=${searchValue.length > 0 ? searchValue : "star-wars"}`
      );
      setRepos(response.data);
      setLoading(false);
    } catch (error) {
      setError("Error fetching repos.");
      setLoading(false);
    }
  };

  if (loading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>{error}</div>;
  }

  return (
    <>
      <SearchBox onSubmit={(value) => setSearchValue(value)} />
      <div className="grid grid-cols-3 md:grid-cols-3 sm:grid-cols-1 gap-10 m-20">
        {repos.map((repo, index) => (
          <Link key={repo.authorName + index} to={`/repo/${repo.authorName}`}>
            <RepoOverviewCard repo={repo} />
          </Link>
        ))}
      </div>
    </>
  );
}

export default HomePage;
