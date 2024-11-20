package expansioncontent.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;

import expansioncontent.expansionContentMod;
import theHexaghost.HexaMod;


// i tried but its not worth it tbh
// you know what lets just try it
public class SuperHexaguard extends AbstractExpansionCard {
    public final static String ID = makeID("SuperHexaguard");


    private static final int BLOCK = 12;
    private static final int UPG_BLOCK = 4;

    public SuperHexaguard() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_hexaghost.png", "expansioncontentResources/images/1024/bg_boss_hexaghost.png");
        tags.add(expansionContentMod.STUDY_HEXAGHOST);
        tags.add(expansionContentMod.STUDY);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = 2;
        isEthereal = true;
        tags.add(HexaMod.AFTERLIFE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        atb(new DrawCardAction(p, 2));
    }

    public void afterlife() {
        blck();
    }


    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPG_BLOCK);
        }
    }

    @Override
    public void initializeDescription() {
        super.initializeDescription();
        String afterlife_name = downfallMod.keywords_and_proper_names.get("afterlife");
        this.keywords.add(afterlife_name);
    }
}



