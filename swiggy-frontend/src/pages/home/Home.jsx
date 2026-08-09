import { Link } from "react-router-dom";

function Home() {

    return (

        <div className="container text-center mt-5">

            <h1 className="display-4 fw-bold">
                Welcome to ByteBite
            </h1>

            <p className="lead mt-3">
                Order your favourite food online.
            </p>

            <Link
                to="/restaurants"
                className="btn btn-danger btn-lg mt-3"
            >
                Explore Restaurants
            </Link>

        </div>
    );
}

export default Home;