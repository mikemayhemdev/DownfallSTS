package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import downfall.downfallMod;
import theHexaghost.HexaMod;
import theHexaghost.util.HexaPurpleTextInterface;

public class BurningQuestion extends AbstractHexaCard implements HexaPurpleTextInterface {
    public final static String ID = makeID("BurningQuestion");

    public BurningQuestion() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        isEthereal = true;
        tags.add(HexaMod.AFTERLIFE);
        HexaMod.loadJokeCardImage(this, "BurningQuestion.png");
        this.keywords.add(downfallMod.keywords_and_proper_names.get("afterlife"));
        if (Settings.language == Settings.GameLanguage.ZHS){
            this.keywords.add("敏捷");
        }else if (Settings.language == Settings.GameLanguage.RUS){
            this.keywords.add("ловкость");
        }else{
            this.keywords.add("dexterity");
        }
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
}
