package expansioncontent.cards;


import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;
import downfall.downfallMod;
import expansioncontent.expansionContentMod;
import theHexaghost.HexaMod;

public class SuperGhostShield extends AbstractExpansionCard {
    public final static String ID = makeID("SuperGhostShield");


    private static final int BLOCK = 7;
    private static final int UPG_BLOCK = 3;
    private static final int MAGIC = 1;

    public SuperGhostShield() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        //todo skill bg instead of power bg
        this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_hexaghost.png", "expansioncontentResources/images/1024/bg_boss_hexaghost.png");
        tags.add(expansionContentMod.STUDY_HEXAGHOST);
        tags.add(expansionContentMod.STUDY);
        baseBlock = BLOCK;
        isEthereal = true;
        baseMagicNumber = magicNumber = MAGIC;
        tags.add(HexaMod.AFTERLIFE);
        expansionContentMod.loadJokeCardImage((AbstractCard)this, "GhostShield.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        applyToSelf(new BlurPower(AbstractDungeon.player, magicNumber));
    }

    @Override
    public void afterlife() {
        blck();
        applyToSelf(new BlurPower(AbstractDungeon.player, magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPG_BLOCK);
        }
    }
    // to still show afterlife tooltip. because the format [purple]hexamod:afterlife[] doesnt get displayed correctly
    // we are only using [purple]afterlife[] here for easier text comprehension for new players, but doing this
    // means we dont have the keyword tooltip so we need to manually add it
    // but after I tried adding it in the constrcutor it turns out sometimes who knows why it wont be added
    // and this way seems to work
    @Override
    public void initializeDescription() {
        super.initializeDescription();
        String afterlife_name = downfallMod.keywords_and_proper_names.get("afterlife");
        this.keywords.add(afterlife_name);
        String blur_name = downfallMod.keywords_and_proper_names.get("blur");
        this.keywords.add(blur_name);
        if (Settings.language == Settings.GameLanguage.ZHS){
            this.keywords.add("格挡");
        }else if (Settings.language == Settings.GameLanguage.RUS){
            //this.keywords.add("ловкость");
        }else{
            this.keywords.add("block");
        }
    }
}


