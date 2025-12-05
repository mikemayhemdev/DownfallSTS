package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import downfall.downfallMod;
import theHexaghost.HexaMod;
import theHexaghost.util.HexaPurpleTextInterface;

public class BurningQuestion extends AbstractHexaCard implements HexaPurpleTextInterface {
    public final static String ID = makeID("BurningQuestion");

    public BurningQuestion() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
        isEthereal = true;
        tags.add(HexaMod.AFTERLIFE);
        HexaMod.loadJokeCardImage(this, "BurningQuestion.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new StrengthPower(p,magicNumber));
        applyToSelf(new DexterityPower(AbstractDungeon.player,1));
    }

    @Override
    public void afterlife() {
        flash();
        applyToSelf(new DexterityPower(AbstractDungeon.player,1));
    }

    @Override
    protected boolean useAfterlifeVFX() {
        return true;
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }

//    @Override
//    public float getTitleFontSize() {
//        if(Settings.language != Settings.GameLanguage.ENG) {
//            return 19.0F;
//        }else {
//            return 20.0F;
//        }
//    }

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
        this.keywords.add(GameDictionary.DEXTERITY.NAMES[0].toLowerCase());
        }
    }
