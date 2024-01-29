
import {create} from 'zustand';

export interface User {
    userId: string;
    email: string;
    profileImageUrl: string | null;
}

interface UserState {
    user: User | null;
    setUser: (user: User) => void;
    updateUserProfileImage: (url: string) => void;
    clearUser: () => void;
}


export const useUserStore = create<UserState>((set) => ({
    user: null,
    setUser: (user: User) => set({ user }),
    updateUserProfileImage: (url: string) => set((state) => ({
        user: state.user ? { ...state.user, profileImageUrl: url } : null
    })),
    clearUser: () => set({ user: null }),
}));