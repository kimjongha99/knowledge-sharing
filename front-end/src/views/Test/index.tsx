import './style.css';

export default function Test(){

    const truncateText = (text: string, maxLength: number) => {
        return text.length > maxLength ? `${text.substring(0, maxLength)}...` : text;
    };

    const title = "Scotch bonnet pepper leek aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaapicnic salad";
    const description = "Lemon tahini dressing vine tomatoes toasted hazelnuts ultra creamy avocado pesto Lemon tahini dressing vine tomatoes toasted hazelnuts ultra creamy avocado pesto Lemon tahini dressing vine tomatoes toasted hazelnuts ultra creamy avocado pesto";


    return(
        <div>

            <ul className="grid">

                <li className="grid__item">
                    <div className="card card--secondary card--reverse">
                        <div className="card__top">
              <span className="card__tag">
                상세보기
              </span>
                        </div>
                        <div className="card__bottom">
                            <h2 className="card__title">{truncateText(title, 20)}</h2>
                            <p className="card__text">{truncateText(description, 70)}</p>

                        </div>
                    </div>
                </li>

            </ul>
        </div>
    )
}


