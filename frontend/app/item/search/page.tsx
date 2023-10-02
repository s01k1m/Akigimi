'use client'
import { BiSearchAlt } from 'react-icons/bi';
import { useRouter } from 'next/navigation';
import React, { useState } from 'react';
import Slider from '@mui/material/Slider';
import Box from '@mui/material/Box';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import Image from 'next/image';
import ItemSearchList from './ItemSearchList';

// 카테고리 컴포넌트화
const CategoryItem = ({ src, alt, title, subTitle, onClick }) => {
  return (
    <div 
        className='flex flex-col items-center'
        onClick={onClick}
    >
      <Image src={src} alt={alt} width={50} height={50} />
      <p className='mt-2'>{title}</p>
      {subTitle && <p>{subTitle}</p>}
    </div>
  );
};

const Categories = ({ setSelectedCategory }) => {
  const categories = [
    { src: '/images/frame.png', alt: 'frame', title: '가전' },
    { src: '/images/airplane.png', alt: 'frame', title: '여가', subTitle: '/생활편의' },
    { src: '/images/pencil.png', alt: 'frame', title: '강의' },
    { src: '/images/furniture.png', alt: 'frame', title: '가구' },
    { src: '/images/clothes.png', alt: 'frame', title: '의류' },
  ];

  return (
    <div className='flex justify-center gap-10'>
      {categories.map((category, index) => (
        <CategoryItem
          key={index}
          src={category.src}
          alt={category.alt}
          title={category.title}
          subTitle={category.subTitle}
          onClick={() => setSelectedCategory(category.title)}
        />
      ))}
    </div>
  );
};

// 슬라이더 커스텀
const theme = createTheme({
    components: {
      MuiSlider: {
        styleOverrides: {
          track: {
            color: 'blue',
          },
          rail: {
            color: 'gray',
          },
          thumb: {
            color: 'blue'
          }
        },
      },
    },
  });


const ItemSearch = () => {
    const router = useRouter();

    // 가격 슬라이더
    const [rangeValue, setRangeValue] = React.useState<number[]>([20, 37]);
    const handleChange = (event: Event, newValue: number | number[]) => {
        setRangeValue(newValue as number[]);
    };

    // input 검색창
    const [inputValue, setInputValue] = useState<string>();
    // 엔터 치면 검색어 전달하기
    const onKeyPress = (e: React.KeyboardEvent<HTMLInputElement>) => {
        if (e.key === 'Enter') {
            console.log('엔터 쳤음', inputValue)
        }
    };

    const onChangeInput = (e: React.ChangeEvent<HTMLInputElement>) => {
        setInputValue(e.target.value);
    };

    // 카테고리
    const [selectedCategory, setSelectedCategory] = useState<string>();

  return (
    <div>
      <div className="flex justify-center mt-3">
        <div className='flex items-center'>
          <BiSearchAlt size={30} className="absolute ms-2" />
          <input
            type="text"
            value={inputValue}
            onChange={onChangeInput}
            onKeyPress={onKeyPress}
            placeholder="미닝템을 검색하세요"
            className='bg-[#EEE] w-[70vw] min-w-[280px] max-w-[400px] h-[50px] rounded ps-[40px]'
          />
        </div>
        <div
          className='w-[50px] h-[50px] bg-tossblue text-white flex justify-center items-center text-[30px] rounded-md ms-1'
          onClick={() => router.push('/item/add')}
        >+</div>
      </div>
      <div className='mt-5'>
        <Categories setSelectedCategory={setSelectedCategory}/>
      </div>
      <div className='flex flex-col items-center mt-8'>
        <div className='w-[85%]'>
            <div>가격대</div>
            <ThemeProvider theme={theme}>
                <Box>
                    <Slider
                        value={rangeValue}
                        onChange={handleChange}
                        valueLabelDisplay="auto"
                    />
                </Box>
            </ThemeProvider>
            <div className='flex justify-between'>
                <div>0원</div>
                <div>100만원</div>
            </div>
        </div>
      </div>
      {/* item component */}
      <div>
        <ItemSearchList value={inputValue} category={selectedCategory} range={rangeValue} />
      </div>
    </div>
  );
};

export default ItemSearch;
