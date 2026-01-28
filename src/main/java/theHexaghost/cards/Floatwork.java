package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import downfall.downfallMod;
import theHexaghost.HexaMod;
import theHexaghost.util.HexaPurpleTextInterface;

public class Floatwork extends AbstractHexaCard implements HexaPurpleTextInterface {
    public final static String ID = makeID("Floatwork");

    public Floatwork() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBurn = burn = 1;
        baseMagicNumber = magicNumber = 2;
       // baseBlock = 5;
        isEthereal = true;
        tags.add(HexaMod.AFTERLIFE);
        HexaMod.loadJokeCardImage(this, "Floatwork.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new DexterityPower(AbstractDungeon.player,1));
        applyToSelf(new PlatedArmorPower(p, magicNumber));
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        if (this.isEthereal) {
            addToBot(new GainBlockAction(AbstractDungeon.player, magicNumber));
            this.addToTop(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
        }

    }

    public void afterlife() {
        applyToSelf(new PlatedArmorPower(AbstractDungeon.player, magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
          //  upgradeBlock(2);
            upgradeMagicNumber(2);
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

        if (Settings.language == Settings.GameLanguage.ZHS || Settings.language == Settings.GameLanguage.ZHT){
            this.keywords.add("多层护甲");
        }else if (Settings.language == Settings.GameLanguage.RUS){
            this.keywords.add("plated");
        }else{
            String blur_name = downfallMod.keywords_and_proper_names.get("plated");
            this.keywords.add(blur_name);
        }


    }
}