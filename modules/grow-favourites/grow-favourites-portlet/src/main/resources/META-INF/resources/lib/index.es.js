import React from "react";
import ReactDOM from "react-dom";
import axios from 'axios';
import { CarouselProvider, Slider, Slide, ButtonBack, ButtonNext } from 'pure-react-carousel';

import GrowFavouritesPortletLeftPanel from './modules/GrowFavouritesPortletLeftPanel.es';
import GrowFavouritesSlide from './modules/GrowFavouritesSlide.es';
import GrowIcon from "./modules/GrowIcon.es";

const SPRITEMAP = Liferay.ThemeDisplay.getPathThemeImages();
const CARDS_PER_COLUMN = 3;
const VISIBLE_SLIDES = 2;
const API = 'https://jsonplaceholder.typicode.com';
const DEFAULT_QUERY = '/todos/1';
const REMOVE_QUERY = '/todos/1';
const ADD_QUERY = '/todos/1';

const newCardMockupData = 		{
		articleAuthor: "New Author",
		authorAvatar: "/o/GrowFavouritesPortlet/images/0.jpeg",
		createDate: "01.01.2019",
		articleTitle: "Title New",
		articleContent: "",
		tags: ["badge", "gamification", "respect", "test1", "test2"],
		readCount: "626",
		articleCategory: "Share",
		id: "card-111",
		star: true
		};

const mockupData = {
	"data": [
		{
		articleAuthor: "Author 01",
		authorAvatar: "/o/GrowFavouritesPortlet/images/0.jpeg",
		createDate: "01.01.2019",
		articleTitle: "Title 01",
		articleContent: "",
		tags: ["badge", "gamification", "respect", "test1", "test2"],
		readCount: "626",
		articleCategory: "Share",
		id: "card-001",
		star: true
		},
		{
		articleAuthor: "Author 02",
		authorAvatar: "/o/GrowFavouritesPortlet/images/0.jpeg",
		createDate: "01.01.2019",
		articleTitle: "Title 02",
		articleContent: "",
		tags: ["badge", "gamification", "respect", "test1", "test2"],
		readCount: "626",
		articleCategory: "Share",
		id: "card-002",
		star: true
		},
		{
		articleAuthor: "Author 03",
		authorAvatar: "/o/GrowFavouritesPortlet/images/0.jpeg",
		createDate: "01.01.2019",
		articleTitle: "Title 03",
		articleContent: "",
		tags: ["badge", "gamification", "respect", "test1", "test2"],
		readCount: "626",
		articleCategory: "Share",
		id: "card-003",
		star: true
		},
		{
		articleAuthor: "Author 04",
		authorAvatar: "/o/GrowFavouritesPortlet/images/0.jpeg",
		createDate: "01.01.2019",
		articleTitle: "Title 04",
		articleContent: "",
		tags: ["badge", "gamification", "respect", "test1", "test2"],
		readCount: "626",
		articleCategory: "Share",
		id: "card-004",
		star: true
		},
		{
		articleAuthor: "Author 05",
		authorAvatar: "/o/GrowFavouritesPortlet/images/0.jpeg",
		createDate: "01.01.2019",
		articleTitle: "Title 05",
		articleContent: "",
		tags: ["badge", "gamification", "respect", "test1", "test2"],
		readCount: "626",
		articleCategory: "Share",
		id: "card-005",
		star: true
		},
		{
		articleAuthor: "Author 06",
		authorAvatar: "/o/GrowFavouritesPortlet/images/0.jpeg",
		createDate: "01.01.2019",
		articleTitle: "Title 06",
		articleContent: "",
		tags: ["badge", "gamification", "respect", "test1", "test2"],
		readCount: "626",
		articleCategory: "Share",
		id: "card-006",
		star: true
		},
		{
		articleAuthor: "Author 07",
		authorAvatar: "/o/GrowFavouritesPortlet/images/0.jpeg",
		createDate: "01.01.2019",
		articleTitle: "Title 07",
		articleContent: "",
		tags: ["badge", "gamification", "respect", "test1", "test2"],
		readCount: "626",
		articleCategory: "Share",
		id: "card-007",
		star: true
		},
		{
		articleAuthor: "Author 08",
		authorAvatar: "/o/GrowFavouritesPortlet/images/0.jpeg",
		createDate: "01.01.2019",
		articleTitle: "Title 08",
		articleContent: "",
		tags: ["badge", "gamification", "respect", "test1", "test2"],
		readCount: "626",
		articleCategory: "Share",
		id: "card-008",
		star: true
		}
	]
};

class App extends React.Component {
	
	constructor(props) {
		super(props);
		
		this.state = {
			data: [],
			isLoading: false,
			error: null,
		};
		
		this.removeCardFromMyFavourites = this.removeCardFromMyFavourites.bind(this);
		this.addCardToMyFavourites = this.addCardToMyFavourites.bind(this);
	}
	
	removeCardFromMyFavourites(data) {
		/*this.setState({ isRemovedFromMyFavourites: false });*/
		
		setTimeout(() => {
			
		axios.get(API + REMOVE_QUERY)
			.then(
				/*response => this.setState({ data: mockupData.data, isLoading: false })*/
				console.log("isRemovedFromMyFavourites")
			)
			.catch(error => this.setState({ error}));
			
		}, 2000);
		

	}
	
	addCardToMyFavourites() {
		
		setTimeout(() => {
			
		axios.get(API + ADD_QUERY)
			.then(
				response => this.setState({data: [newCardMockupData].concat(this.state.data)})
			)
			.catch(error => this.setState({ error}));
			
		}, 2000);
		
	}
	
	componentDidMount() {
		this.setState({ isLoading: true });
		
		setTimeout(() => {
			
		axios.get(API + DEFAULT_QUERY)
			.then(
				response => this.setState({ data: mockupData.data, isLoading: false })
			)
			.catch(error => this.setState({ error, isLoading: false }));
			
		}, 2000);
	}
	
	render() {
		
		const { data, isLoading, error } = this.state;
		
		if (error) {
			 
			return (
				<p>{error.message}</p>
			);
			
		} else if (isLoading) {
			
			return (
				<div className="grow-favourites-porltet">
					<div className="container">
					  <div className="row">
						<div className="col-sm-4">
						
							<GrowFavouritesPortletLeftPanel />
							
						</div>
						<div className="col-sm-8">
						
							<p>Loading ...</p>
					
						</div>
					  </div>
					</div>
				</div>
			)
			
		} else {
			
			let i=0,index=0;
			const growFavouritesSlides = []
			
			while(i< data.length){						
				
				let dataSlide = data.filter(function(value, idx, Arr) {
					return idx >= (0 + i) && idx < (CARDS_PER_COLUMN + i);
				});
				
				growFavouritesSlides.push(
					<Slide index={index} key={index}>
						<GrowFavouritesSlide
							spritemap={SPRITEMAP}
							data={dataSlide}
							slideIndex={index}
							handleStarClick={this.removeCardFromMyFavourites}
						/>
					</Slide>
				);
				
				i += CARDS_PER_COLUMN;
				index++;
			}

			return (
				<div className="grow-favourites-porltet">
					<div className="container">
					  <div className="row">
						<div className="col-sm-4">
						
							<GrowFavouritesPortletLeftPanel />
							
							<button type="button" onClick={this.addCardToMyFavourites}>
								Add to My Favourite
							</button>
							
						</div>
						<div className="col-sm-8">
						
							<CarouselProvider
								naturalSlideWidth={400}
								naturalSlideHeight={520}
								totalSlides={index}
								visibleSlides={VISIBLE_SLIDES}
							>
								<ButtonBack
									className={"grow-favourites-carousel-button-back"}>
									<GrowIcon
										spritemap={SPRITEMAP}
										classes="lexicon-icon inline-item"
										iconName="angle-left"
									/>
								</ButtonBack>
								<Slider>
									{growFavouritesSlides}
								</Slider>		
								<ButtonNext
									className={"grow-favourites-carousel-button-next"}>
									<GrowIcon
										spritemap={SPRITEMAP}
										classes="lexicon-icon inline-item"
										iconName="angle-right"
									/>
								</ButtonNext>
							</CarouselProvider>
					
						</div>
					  </div>
					</div>
				</div>
			);
		}
	}
}

export default function(elementId) {
	ReactDOM.render(<App />, document.getElementById(elementId));
}