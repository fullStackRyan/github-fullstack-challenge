import { FC } from "react";
import { Repository } from "../types/Types";
import { StarIcon, EyeIcon } from "@heroicons/react/24/solid";

type Props = {
  repo: Repository;
};

const RepoOverviewCard: FC<Props> = ({ repo }) => {
  return (
    <div className="rounded-lg shadow-lg p-4 bg-red transform transition-all duration-200 hover:scale-105">
      <div className="grid grid-cols-1 gap-4">
        <div>
          <img src={repo.image} alt="Repository" />
        </div>
        <div className="mt-4">
          <h3 className="text-lg font-semibold">{repo.repoName}</h3>
          <p className="text-lg text-gray-500">{repo.authorName}</p>
        </div>
        <div className="flex justify-start">
          <div className="flex items-center mr-4">
            <StarIcon className="h-5 w-5 text-yellow-500 mr-2" />
            <p className="text-lg text-yellow-500">{repo.stars}</p>
          </div>
          <div className="flex items-center mr-4">
            <EyeIcon className="h-5 w-5 text-blue-500 mr-2" />
            <p className="text-lg text-blue-500">{repo.watchers}</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default RepoOverviewCard;
