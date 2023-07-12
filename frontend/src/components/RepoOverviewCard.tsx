import React from "react";
import { Repository } from "../types/Repository";
import { StarIcon, EyeIcon } from "@heroicons/react/24/solid";

type Props = {
  repo: Repository;
};

const RepoOverviewCard: React.FC<Props> = ({ repo }) => {
  return (
    <div className="rounded-lg shadow-lg p-4 bg-red transform transition-all duration-200 hover:scale-105">
      <div className="grid grid-cols-2 gap-4">
        <div className="col-span-2">
          <img src={repo.image} alt="Repository" />
        </div>
        <div className="col-span-2 mt-4">
          <h3 className="text-lg font-semibold">{repo.repoName}</h3>
          <p className="text-lg text-gray-500">{repo.authorName}</p>
        </div>
        <div className="flex items-center">
          <StarIcon className="h-5 w-5 text-yellow-500 mr-2" />
          <p className="text-lg text-yellow-500">{repo.stars}</p>
        </div>
        <div className="flex items-center">
          <EyeIcon className="h-5 w-5 text-gray-500 mr-2" />
          <p className="text-lg text-gray-500">{repo.watchers}</p>
        </div>
      </div>
    </div>
  );
};

export default RepoOverviewCard;
