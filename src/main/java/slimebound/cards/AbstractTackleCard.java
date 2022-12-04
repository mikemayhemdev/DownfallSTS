package slimebound.cards;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import downfall.downfallMod;
import slimebound.SlimeboundMod;
import theHexaghost.HexaMod;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTackleCard extends AbstractSlimeboundCard {
    private String[] descriptorStrings = CardCrawlGame.languagePack.getUIString(SlimeboundMod.makeID("tackleDescriptor")).TEXT;

    public AbstractTackleCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
        if (downfallMod.disableDescriptors && !this.rawDescription.contains(descriptorStrings[1])) {
            this.rawDescription = descriptorStrings[1] + this.rawDescription;
            initializeDescription();
        }
    }

    @Override
    public List<String> getCardDescriptors() {
        List<String> tags = new ArrayList<>();
        if (!downfallMod.disableDescriptors)
            tags.add(descriptorStrings[0]);
        return tags;
    }

    @Override
    public List<TooltipInfo> getCustomTooltipsTop() {
        List<TooltipInfo> tips = new ArrayList<>();
        if (!downfallMod.disableDescriptors && !keywords.contains(SlimeboundMod.makeID(descriptorStrings[0]).toLowerCase()))
            tips.add(new TooltipInfo(BaseMod.getKeywordTitle("slimeboundmod:tackle"), BaseMod.getKeywordDescription("slimeboundmod:tackle")));
        return tips;
    }
}
