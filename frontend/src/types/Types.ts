export type Repository = {
  authorName: string;
  image: URL;
  repoName: string;
  stars: number;
  watchers: number;
};

export type RepositoryOverview = {
  avatar: URL;
  lastUpdated: Date;
  author: string;
  programmingLanguage: string;
};

export type URL = string;
