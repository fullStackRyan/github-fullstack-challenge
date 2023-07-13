import { FC } from "react";
import { RepositoryOverview } from "../types/Types";

type Props = {
  overview: RepositoryOverview;
};

const RepoDetailsCard: FC<Props> = ({ overview }) => {
  const { avatar, lastUpdated, author, programmingLanguage } = overview;
  return (
    <div className="flex justify-center items-center h-screen ">
      <div className="flex flex-col max-w-xl mx-auto shadow-xl rounded-xl p-8 space-y-6 m-6 bg-white">
        <div className="flex justify-center">
          <img
            className="w-80 h-80 object-contain rounded-"
            src={avatar}
            alt="avatar"
          />
        </div>
        <div>
          <div className="font-bold text-3xl text-slate-500 text-center">
            {author}
          </div>
          <p className="text-center text-slate-500 text-lg">
            Last updated: {lastUpdated.toLocaleDateString()}
          </p>
        </div>
        <div className="px-3 py-3 bg-gradient-to-r from-yellow-400 via-red-500 to-pink-500 text-white rounded-md">
          <p className="text-center text-md font-bold">
            #{programmingLanguage}
          </p>
        </div>
      </div>
    </div>
  );
};

export default RepoDetailsCard;
