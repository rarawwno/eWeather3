package com.eweather.eweather3.response;

import java.util.List;

public class DistrictsList {
    String status;
    List<Country> districts;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Country> getDistricts() {
        return districts;
    }

    public void setDistricts(List<Country> districts) {
        this.districts = districts;
    }

    public class Country{
        String adcode;
        String name;
        List<Province> districts;

        public String getAdcode() {
            return adcode;
        }

        public void setAdcode(String adcode) {
            this.adcode = adcode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Province> getDistricts() {
            return districts;
        }

        public void setDistricts(List<Province> districts) {
            this.districts = districts;
        }

        public class Province{
            String adcode;
            String name;
            List<City> districts;

            public String getAdcode() {
                return adcode;
            }

            public void setAdcode(String adcode) {
                this.adcode = adcode;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<City> getDistricts() {
                return districts;
            }

            public void setDistricts(List<City> districts) {
                this.districts = districts;
            }
            public class City{
                String adcode;
                String name;
                List<County> districts;

                public String getAdcode() {
                    return adcode;
                }

                public void setAdcode(String adcode) {
                    this.adcode = adcode;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public List<County> getDistricts() {
                    return districts;
                }

                public void setDistricts(List<County> districts) {
                    this.districts = districts;
                }

                public class County{
                    String adcode;
                    String name;

                    public String getAdcode() {
                        return adcode;
                    }

                    public void setAdcode(String adcode) {
                        this.adcode = adcode;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }
                }
            }
        }
    }
}
