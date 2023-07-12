import { FC } from "react";
import { Repository } from "../types/Repository";

type Props = {
  repo: Repository;
};

const RepoOverviewCard: FC<Props> = ({ repo }) => {
  return (
    <div className="rounded-lg shadow-lg p-4 bg-red">
      <div className="grid grid-cols-2 gap-4">
        <div className="col-span-2">
          <img src={repo.image} alt="Repository" />
        </div>
        <div className="col-span-2 mt-4">
          <h3 className="text-lg font-semibold">{repo.repoName}</h3>
          <p className="text-sm text-gray-500">{repo.authorName}</p>
        </div>
        <div>
          <p className="text-sm text-red-500">Stars: {repo.stars}</p>
        </div>
        <div>
          <p className="text- text-gray-500">Watchers: {repo.watchers}</p>
        </div>
      </div>
    </div>
  );
};

export default RepoOverviewCard;
