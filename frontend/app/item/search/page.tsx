'use client'
import { BiSearchAlt } from 'react-icons/bi';
import { useRouter } from 'next/navigation';
import React from 'react';
import Slider from '@mui/material/Slider';
import Box from '@mui/material/Box';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import Image from 'next/image';
import ItemSearchList from './ItemSearchList';

const CategoryItem = ({ src, alt, title, subTitle }) => {
  return (
    <div className='flex flex-col items-center'
        onClick={() => {console.log(title)}}
    >
      <Image src={src} alt={alt} width={50} height={50} />
      <p className='mt-2'>{title}</p>
      {subTitle && <p>{subTitle}</p>}
    </div>
  );
};

const Categories = () => {
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

function valuetext(value: number) {
    return `${value}°C`;
  }


const ItemSearch = () => {
    const router = useRouter();

    // 가격 슬라이더
    const [value, setValue] = React.useState<number[]>([20, 37]);
    const handleChange = (event: Event, newValue: number | number[]) => {
        setValue(newValue as number[]);
    };

  return (
    <div>
      <div className="flex justify-center mt-3">
        <div className='flex items-center'>
          <BiSearchAlt size={30} className="absolute ms-2" />
          <input
            type="text"
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
        <Categories />
      </div>
      <div className='flex flex-col items-center mt-8'>
        <div className='w-[85%]'>
            <div>가격대</div>
            <ThemeProvider theme={theme}>
                <Box>
                    <Slider
                        value={value}
                        onChange={handleChange}
                        valueLabelDisplay="auto"
                        getAriaValueText={valuetext}
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
        <ItemSearchList />
      </div>
    </div>
  );
};

export default ItemSearch;
