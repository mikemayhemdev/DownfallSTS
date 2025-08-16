package theHexaghost.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;
import downfall.downfallMod;
import expansioncontent.expansionContentMod;
import theHexaghost.HexaMod;
import theHexaghost.util.HexaPurpleTextInterface;

public class GhostShield extends AbstractHexaCard implements HexaPurpleTextInterface {

    public final static String ID = makeID("GhostShield");

    private static final int BLOCK = 6;
    private static final int MAGIC = 1;
    private static final int UPG_BLOCK = 3;

    public GhostShield() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
        isEthereal = true;
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
        this.keywords.add(GameDictionary.BLOCK.NAMES[0].toLowerCase());
            }
        }
