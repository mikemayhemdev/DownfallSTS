package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;
import theHexaghost.HexaMod;
import theHexaghost.util.HexaPurpleTextInterface;

public class Hexaguard extends AbstractHexaCard implements HexaPurpleTextInterface {
    public final static String ID = makeID("Hexaguard");

    private static final int UPG_BLOCK = 3;

    public Hexaguard() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 6;
        isEthereal = true;
        baseMagicNumber = magicNumber = 2;
        tags.add(HexaMod.AFTERLIFE);
        HexaMod.loadJokeCardImage(this, "Hexaguard.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        atb(new DrawCardAction(p, this.magicNumber));
    }

    public void afterlife() {
        blck();
//        atb(new DrawCardAction(AbstractDungeon.player, this.magicNumber));
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
        if (Settings.language == Settings.GameLanguage.ZHS){
            this.keywords.add("格挡");
        }else if (Settings.language == Settings.GameLanguage.RUS){
            //this.keywords.add("ловкость");
        }else{
            this.keywords.add("block");
        }
    }
}